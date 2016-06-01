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
        vm.changeCategory= changeCategory;
        vm.changeSubCategory= changeSubCategory;
        vm.categories = Category.enabled();
        vm.request.dateRequest= new Date();
        vm.subcategories=null;

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

        function changeCategory(){
          vm.subcategories = Subcategory.enabled(
	                {category : vm.request.category.id},
	                function (value, responseHeaders) {

	                },
	                function (httpResponse) {

	                }
	            );
        }

        function changeSubCategory(){
          vm.request.subcategory = Subcategory.get(
	                {id : vm.request.subcategory.id},
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
        vm.datePickerOpenStatus.created_date = false;
        vm.datePickerOpenStatus.last_modified_date = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
