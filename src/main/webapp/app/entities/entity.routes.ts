import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'dunderApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'departamento',
    data: { pageTitle: 'dunderApp.departamento.home.title' },
    loadChildren: () => import('./departamento/departamento.routes'),
  },
  {
    path: 'jefaturas',
    data: { pageTitle: 'dunderApp.jefaturas.home.title' },
    loadChildren: () => import('./jefaturas/jefaturas.routes'),
  },
  {
    path: 'empleados',
    data: { pageTitle: 'dunderApp.empleados.home.title' },
    loadChildren: () => import('./empleados/empleados.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
