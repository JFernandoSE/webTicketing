(function() {
    'use strict';

    angular
        .module('demoApp')
        .controller('CategoryDetailController', CategoryDetailController);

    CategoryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Category', 'CategoryLang', 'Subcategory', 'Request'];

    function CategoryDetailController($scope, $rootScope, $stateParams, entity, Category, CategoryLang, Subcategory, Request) {
        var vm = this;

        vm.category = entity;

        var unsubscribe = $rootScope.$on('demoApp:categoryUpdate', function(event, result) {
            vm.category = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
