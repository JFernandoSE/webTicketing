(function() {
    'use strict';

    angular
        .module('demoApp')
        .controller('ActionLangDetailController', ActionLangDetailController);

    ActionLangDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'ActionLang', 'Action'];

    function ActionLangDetailController($scope, $rootScope, $stateParams, entity, ActionLang, Action) {
        var vm = this;

        vm.actionLang = entity;

        var unsubscribe = $rootScope.$on('demoApp:actionLangUpdate', function(event, result) {
            vm.actionLang = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
