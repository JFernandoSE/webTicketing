(function() {
    'use strict';

    angular
        .module('demoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('category-lang', {
            parent: 'entity',
            url: '/category-lang?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'demoApp.categoryLang.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/category-lang/category-langs.html',
                    controller: 'CategoryLangController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('categoryLang');
                    $translatePartialLoader.addPart('language');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('category-lang-detail', {
            parent: 'entity',
            url: '/category-lang/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'demoApp.categoryLang.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/category-lang/category-lang-detail.html',
                    controller: 'CategoryLangDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('categoryLang');
                    $translatePartialLoader.addPart('language');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CategoryLang', function($stateParams, CategoryLang) {
                    return CategoryLang.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('category-lang.new', {
            parent: 'category-lang',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/category-lang/category-lang-dialog.html',
                    controller: 'CategoryLangDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                languageCode: null,
                                value: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('category-lang', null, { reload: true });
                }, function() {
                    $state.go('category-lang');
                });
            }]
        })
        .state('category-lang.edit', {
            parent: 'category-lang',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/category-lang/category-lang-dialog.html',
                    controller: 'CategoryLangDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CategoryLang', function(CategoryLang) {
                            return CategoryLang.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('category-lang', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('category-lang.delete', {
            parent: 'category-lang',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/category-lang/category-lang-delete-dialog.html',
                    controller: 'CategoryLangDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CategoryLang', function(CategoryLang) {
                            return CategoryLang.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('category-lang', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
