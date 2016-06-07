(function() {
    'use strict';

    angular
        .module('demoApp')
        .controller('RequestDialogController', RequestDialogController);

    RequestDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Request', 'Category', 'CategoryLang', 'Subcategory', 'SubcategoryLang', 'Action', 'ActionLang', '$translate'];

    function RequestDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Request, Category, CategoryLang, Subcategory, SubcategoryLang, Action, ActionLang,  $translate) {
        var vm = this;
        vm.request = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.changeCategory= changeCategory;
        vm.changeSubCategory= changeSubCategory;

        vm.code=$translate.use();
        vm.categories = CategoryLang.language(
          {language : $translate.use()},
            function (value, responseHeaders) {
                },
            function (httpResponse) {
                }
        );


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
            vm.request.category=vm.category.category;
            vm.request.subcategory=vm.subcategory.subcategory;
            vm.request.accion=vm.accion.accion;
            if (vm.request.id !== null) {
                Request.update(vm.request, onSaveSuccess, onSaveError);
            } else {
                Request.save(vm.request, onSaveSuccess, onSaveError);
            }
        }

        function changeCategory(){
          vm.subcategories = SubcategoryLang.language(
                  { language : $translate.use(),
                    category : vm.category.category.id
                  },
	                function (value, responseHeaders) {
                    vm.actions=null;
                    vm.action=null;
	                },
	                function (httpResponse) {

	                }
	            );
        }

        function changeSubCategory(){
          if(vm.subcategory!=null){
            vm.actions = ActionLang.language(
  	                {  language : $translate.use(),
                       subcategory : vm.subcategory.subcategory.id
                    },
  	                function (value, responseHeaders) {
  	                },
  	                function (httpResponse) {
  	                }
  	            );
          }
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
