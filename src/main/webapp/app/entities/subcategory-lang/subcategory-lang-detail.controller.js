(function() {
    'use strict';

    angular
        .module('demoApp')
        .controller('SubcategoryLangDetailController', SubcategoryLangDetailController);

    SubcategoryLangDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'SubcategoryLang', 'Subcategory'];

    function SubcategoryLangDetailController($scope, $rootScope, $stateParams, entity, SubcategoryLang, Subcategory) {
        var vm = this;

        vm.subcategoryLang = entity;

        var unsubscribe = $rootScope.$on('demoApp:subcategoryLangUpdate', function(event, result) {
            vm.subcategoryLang = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
