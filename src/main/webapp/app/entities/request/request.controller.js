(function() {
    'use strict';

    angular
        .module('demoApp')
        .controller('RequestController', RequestController);

    RequestController.$inject = ['$scope', '$state', 'Request', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants', 'Principal'];

    function RequestController ($scope, $state, Request, ParseLinks, AlertService, pagingParams, paginationConstants, Principal) {
        var vm = this;

        vm.account = null;
        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                loadAll();
            });
        }

        function loadAll () {
            Request.byusers({
                user: vm.account.login
            }, onSuccess, onError);
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.requests = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadPage (page) {
            vm.page = page;
            vm.transition();
        }

        function transition () {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }
    }
})();
