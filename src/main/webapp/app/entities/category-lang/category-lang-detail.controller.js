(function() {
    'use strict';

    angular
        .module('demoApp')
        .controller('CategoryLangDetailController', CategoryLangDetailController);

    CategoryLangDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'CategoryLang', 'Category'];

    function CategoryLangDetailController($scope, $rootScope, $stateParams, entity, CategoryLang, Category) {
        var vm = this;

        vm.categoryLang = entity;

        var unsubscribe = $rootScope.$on('demoApp:categoryLangUpdate', function(event, result) {
            vm.categoryLang = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
