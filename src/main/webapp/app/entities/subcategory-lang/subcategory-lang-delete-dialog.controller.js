(function() {
    'use strict';

    angular
        .module('demoApp')
        .controller('SubcategoryLangDeleteController',SubcategoryLangDeleteController);

    SubcategoryLangDeleteController.$inject = ['$uibModalInstance', 'entity', 'SubcategoryLang'];

    function SubcategoryLangDeleteController($uibModalInstance, entity, SubcategoryLang) {
        var vm = this;

        vm.subcategoryLang = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SubcategoryLang.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
