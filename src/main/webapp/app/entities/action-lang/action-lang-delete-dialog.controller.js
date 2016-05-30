(function() {
    'use strict';

    angular
        .module('demoApp')
        .controller('ActionLangDeleteController',ActionLangDeleteController);

    ActionLangDeleteController.$inject = ['$uibModalInstance', 'entity', 'ActionLang'];

    function ActionLangDeleteController($uibModalInstance, entity, ActionLang) {
        var vm = this;

        vm.actionLang = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ActionLang.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
