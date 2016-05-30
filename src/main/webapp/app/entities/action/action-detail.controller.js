(function() {
    'use strict';

    angular
        .module('demoApp')
        .controller('ActionDetailController', ActionDetailController);

    ActionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Action', 'ActionLang', 'Request', 'Subcategory'];

    function ActionDetailController($scope, $rootScope, $stateParams, entity, Action, ActionLang, Request, Subcategory) {
        var vm = this;

        vm.action = entity;

        var unsubscribe = $rootScope.$on('demoApp:actionUpdate', function(event, result) {
            vm.action = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
