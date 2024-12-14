import { IDepartamento, NewDepartamento } from './departamento.model';

export const sampleWithRequiredData: IDepartamento = {
  id: 19221,
};

export const sampleWithPartialData: IDepartamento = {
  id: 20192,
  ubicacion_depto: 'for vivid costume',
  presupuesto_depto: 13074,
};

export const sampleWithFullData: IDepartamento = {
  id: 9091,
  nombre_depto: 'why than',
  ubicacion_depto: 'unlike owlishly',
  presupuesto_depto: 30334,
};

export const sampleWithNewData: NewDepartamento = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
