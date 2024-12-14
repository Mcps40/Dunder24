import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import JefaturasResolve from './route/jefaturas-routing-resolve.service';

const jefaturasRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/jefaturas.component').then(m => m.JefaturasComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/jefaturas-detail.component').then(m => m.JefaturasDetailComponent),
    resolve: {
      jefaturas: JefaturasResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/jefaturas-update.component').then(m => m.JefaturasUpdateComponent),
    resolve: {
      jefaturas: JefaturasResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/jefaturas-update.component').then(m => m.JefaturasUpdateComponent),
    resolve: {
      jefaturas: JefaturasResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default jefaturasRoute;
