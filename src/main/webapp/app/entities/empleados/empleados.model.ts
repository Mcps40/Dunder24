import { IDepartamento } from 'app/entities/departamento/departamento.model';
import { IJefaturas } from 'app/entities/jefaturas/jefaturas.model';

export interface IEmpleados {
  id: number;
  nombre_empleado?: string | null;
  apellido_empleado?: string | null;
  telefono_empleado?: string | null;
  correo_empleado?: string | null;
  departamento?: IDepartamento | null;
  jefatura?: IJefaturas | null;
}

export type NewEmpleados = Omit<IEmpleados, 'id'> & { id: null };
