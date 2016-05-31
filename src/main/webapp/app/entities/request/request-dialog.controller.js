(function() {
    'use strict';

    angular
        .module('demoApp')
        .controller('RequestDialogController', RequestDialogController);

    RequestDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Request', 'Category', 'Subcategory', 'Action'];

    function RequestDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Request, Category, Subcategory, Action) {
        var vm = this;

        vm.request = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.change = change;
        vm.categories = Category.enabled();
        //vm.subcategories = Subcategory.enabled();
        vm.actions = Action.enabled();
        vm.request.dateRequest= new Date();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.request.id !== null) {
                Request.update(vm.request, onSaveSuccess, onSaveError);
            } else {
                Request.save(vm.request, onSaveSuccess, onSaveError);
            }
        }

        function change () {
          /*m.subcategories = Subcategory.enabled(
                 {category:vm.request.category.id}*/

          vm.subcategories = Subcategory.enabled(
	           {category:vm.request.category.id},
	            function (value, responseHeaders) {
	            },
	            function (httpResponse) {
	            }
	        );
        }

        function onSaveSuccess (result) {
            $scope.$emit('demoApp:requestUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateRequest = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
