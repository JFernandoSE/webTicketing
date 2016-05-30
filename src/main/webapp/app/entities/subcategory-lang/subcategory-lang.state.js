(function() {
    'use strict';

    angular
        .module('demoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('subcategory-lang', {
            parent: 'entity',
            url: '/subcategory-lang?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'demoApp.subcategoryLang.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/subcategory-lang/subcategory-langs.html',
                    controller: 'SubcategoryLangController',
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
                    $translatePartialLoader.addPart('subcategoryLang');
                    $translatePartialLoader.addPart('language');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('subcategory-lang-detail', {
            parent: 'entity',
            url: '/subcategory-lang/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'demoApp.subcategoryLang.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/subcategory-lang/subcategory-lang-detail.html',
                    controller: 'SubcategoryLangDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('subcategoryLang');
                    $translatePartialLoader.addPart('language');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SubcategoryLang', function($stateParams, SubcategoryLang) {
                    return SubcategoryLang.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('subcategory-lang.new', {
            parent: 'subcategory-lang',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subcategory-lang/subcategory-lang-dialog.html',
                    controller: 'SubcategoryLangDialogController',
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
                    $state.go('subcategory-lang', null, { reload: true });
                }, function() {
                    $state.go('subcategory-lang');
                });
            }]
        })
        .state('subcategory-lang.edit', {
            parent: 'subcategory-lang',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subcategory-lang/subcategory-lang-dialog.html',
                    controller: 'SubcategoryLangDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SubcategoryLang', function(SubcategoryLang) {
                            return SubcategoryLang.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('subcategory-lang', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('subcategory-lang.delete', {
            parent: 'subcategory-lang',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subcategory-lang/subcategory-lang-delete-dialog.html',
                    controller: 'SubcategoryLangDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SubcategoryLang', function(SubcategoryLang) {
                            return SubcategoryLang.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('subcategory-lang', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
