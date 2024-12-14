import { IDepartamento } from 'app/entities/departamento/departamento.model';

export interface IJefaturas {
  id: number;
  nombre_jefe?: string | null;
  departamento?: IDepartamento | null;
}

export type NewJefaturas = Omit<IJefaturas, 'id'> & { id: null };
