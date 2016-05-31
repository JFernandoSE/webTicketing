(function() {
    'use strict';

    angular
        .module('demoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('subcategory', {
            parent: 'entity',
            url: '/subcategory?page&sort&search',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'demoApp.subcategory.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/subcategory/subcategories.html',
                    controller: 'SubcategoryController',
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
                    $translatePartialLoader.addPart('subcategory');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('subcategory-detail', {
            parent: 'entity',
            url: '/subcategory/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'demoApp.subcategory.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/subcategory/subcategory-detail.html',
                    controller: 'SubcategoryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('subcategory');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Subcategory', function($stateParams, Subcategory) {
                    return Subcategory.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('subcategory.new', {
            parent: 'subcategory',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subcategory/subcategory-dialog.html',
                    controller: 'SubcategoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                externalId: null,
                                enabled: false,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('subcategory', null, { reload: true });
                }, function() {
                    $state.go('subcategory');
                });
            }]
        })
        .state('subcategory.edit', {
            parent: 'subcategory',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subcategory/subcategory-dialog.html',
                    controller: 'SubcategoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Subcategory', function(Subcategory) {
                            return Subcategory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('subcategory', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('subcategory.delete', {
            parent: 'subcategory',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subcategory/subcategory-delete-dialog.html',
                    controller: 'SubcategoryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Subcategory', function(Subcategory) {
                            return Subcategory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('subcategory', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
