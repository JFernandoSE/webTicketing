(function() {
    'use strict';

    angular
        .module('demoApp')
        .controller('CategoryLangDeleteController',CategoryLangDeleteController);

    CategoryLangDeleteController.$inject = ['$uibModalInstance', 'entity', 'CategoryLang'];

    function CategoryLangDeleteController($uibModalInstance, entity, CategoryLang) {
        var vm = this;

        vm.categoryLang = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CategoryLang.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
