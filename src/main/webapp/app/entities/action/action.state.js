(function() {
    'use strict';

    angular
        .module('demoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('action', {
            parent: 'entity',
            url: '/action?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'demoApp.action.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/action/actions.html',
                    controller: 'ActionController',
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
                    $translatePartialLoader.addPart('action');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('action-detail', {
            parent: 'entity',
            url: '/action/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'demoApp.action.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/action/action-detail.html',
                    controller: 'ActionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('action');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Action', function($stateParams, Action) {
                    return Action.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('action.new', {
            parent: 'action',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/action/action-dialog.html',
                    controller: 'ActionDialogController',
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
                    $state.go('action', null, { reload: true });
                }, function() {
                    $state.go('action');
                });
            }]
        })
        .state('action.edit', {
            parent: 'action',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/action/action-dialog.html',
                    controller: 'ActionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Action', function(Action) {
                            return Action.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('action', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('action.delete', {
            parent: 'action',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/action/action-delete-dialog.html',
                    controller: 'ActionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Action', function(Action) {
                            return Action.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('action', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
