import { IEmpleados, NewEmpleados } from './empleados.model';

export const sampleWithRequiredData: IEmpleados = {
  id: 10965,
};

export const sampleWithPartialData: IEmpleados = {
  id: 20280,
  correo_empleado: 'rewarding chasuble',
};

export const sampleWithFullData: IEmpleados = {
  id: 14612,
  nombre_empleado: 'draft',
  apellido_empleado: 'round bleakly',
  telefono_empleado: 'hm up warm',
  correo_empleado: 'willfully state',
};

export const sampleWithNewData: NewEmpleados = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
