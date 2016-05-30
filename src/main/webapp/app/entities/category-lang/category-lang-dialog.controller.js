(function() {
    'use strict';

    angular
        .module('demoApp')
        .controller('CategoryLangDialogController', CategoryLangDialogController);

    CategoryLangDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CategoryLang', 'Category'];

    function CategoryLangDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CategoryLang, Category) {
        var vm = this;

        vm.categoryLang = entity;
        vm.clear = clear;
        vm.save = save;
        vm.categories = Category.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.categoryLang.id !== null) {
                CategoryLang.update(vm.categoryLang, onSaveSuccess, onSaveError);
            } else {
                CategoryLang.save(vm.categoryLang, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('demoApp:categoryLangUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
