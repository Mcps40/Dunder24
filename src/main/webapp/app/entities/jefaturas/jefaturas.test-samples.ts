import { IJefaturas, NewJefaturas } from './jefaturas.model';

export const sampleWithRequiredData: IJefaturas = {
  id: 9031,
};

export const sampleWithPartialData: IJefaturas = {
  id: 12486,
};

export const sampleWithFullData: IJefaturas = {
  id: 27728,
  nombre_jefe: 'grumpy excluding',
};

export const sampleWithNewData: NewJefaturas = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
