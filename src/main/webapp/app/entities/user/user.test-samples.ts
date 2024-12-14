import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 13140,
  login: 'dY@WgFSm\\VOmU0\\7eJnXZ',
};

export const sampleWithPartialData: IUser = {
  id: 5105,
  login: '2',
};

export const sampleWithFullData: IUser = {
  id: 26982,
  login: 'n',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
