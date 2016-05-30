(function() {
    'use strict';

    angular
        .module('demoApp')
        .controller('RequestDetailController', RequestDetailController);

    RequestDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Request', 'Category', 'Subcategory', 'Action'];

    function RequestDetailController($scope, $rootScope, $stateParams, entity, Request, Category, Subcategory, Action) {
        var vm = this;

        vm.request = entity;

        var unsubscribe = $rootScope.$on('demoApp:requestUpdate', function(event, result) {
            vm.request = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
