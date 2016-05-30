(function() {
    'use strict';

    angular
        .module('demoApp')
        .controller('SubcategoryLangDialogController', SubcategoryLangDialogController);

    SubcategoryLangDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SubcategoryLang', 'Subcategory'];

    function SubcategoryLangDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SubcategoryLang, Subcategory) {
        var vm = this;

        vm.subcategoryLang = entity;
        vm.clear = clear;
        vm.save = save;
        vm.subcategories = Subcategory.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.subcategoryLang.id !== null) {
                SubcategoryLang.update(vm.subcategoryLang, onSaveSuccess, onSaveError);
            } else {
                SubcategoryLang.save(vm.subcategoryLang, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('demoApp:subcategoryLangUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
