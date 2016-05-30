(function() {
    'use strict';

    angular
        .module('demoApp')
        .controller('ActionLangDialogController', ActionLangDialogController);

    ActionLangDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ActionLang', 'Action'];

    function ActionLangDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ActionLang, Action) {
        var vm = this;

        vm.actionLang = entity;
        vm.clear = clear;
        vm.save = save;
        vm.actions = Action.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.actionLang.id !== null) {
                ActionLang.update(vm.actionLang, onSaveSuccess, onSaveError);
            } else {
                ActionLang.save(vm.actionLang, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('demoApp:actionLangUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
