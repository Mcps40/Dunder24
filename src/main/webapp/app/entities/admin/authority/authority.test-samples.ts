import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: '2db5b67b-5cac-4802-9a5a-bcc466983ac2',
};

export const sampleWithPartialData: IAuthority = {
  name: '8e9116bd-5563-48d5-ad93-6f48286e6250',
};

export const sampleWithFullData: IAuthority = {
  name: '88dff240-669c-4e7f-85fb-cecee46183d3',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
