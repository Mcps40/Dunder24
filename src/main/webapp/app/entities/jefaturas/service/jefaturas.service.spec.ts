import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IJefaturas } from '../jefaturas.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../jefaturas.test-samples';

import { JefaturasService } from './jefaturas.service';

const requireRestSample: IJefaturas = {
  ...sampleWithRequiredData,
};

describe('Jefaturas Service', () => {
  let service: JefaturasService;
  let httpMock: HttpTestingController;
  let expectedResult: IJefaturas | IJefaturas[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(JefaturasService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Jefaturas', () => {
      const jefaturas = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(jefaturas).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Jefaturas', () => {
      const jefaturas = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(jefaturas).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Jefaturas', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Jefaturas', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Jefaturas', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addJefaturasToCollectionIfMissing', () => {
      it('should add a Jefaturas to an empty array', () => {
        const jefaturas: IJefaturas = sampleWithRequiredData;
        expectedResult = service.addJefaturasToCollectionIfMissing([], jefaturas);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(jefaturas);
      });

      it('should not add a Jefaturas to an array that contains it', () => {
        const jefaturas: IJefaturas = sampleWithRequiredData;
        const jefaturasCollection: IJefaturas[] = [
          {
            ...jefaturas,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addJefaturasToCollectionIfMissing(jefaturasCollection, jefaturas);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Jefaturas to an array that doesn't contain it", () => {
        const jefaturas: IJefaturas = sampleWithRequiredData;
        const jefaturasCollection: IJefaturas[] = [sampleWithPartialData];
        expectedResult = service.addJefaturasToCollectionIfMissing(jefaturasCollection, jefaturas);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(jefaturas);
      });

      it('should add only unique Jefaturas to an array', () => {
        const jefaturasArray: IJefaturas[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const jefaturasCollection: IJefaturas[] = [sampleWithRequiredData];
        expectedResult = service.addJefaturasToCollectionIfMissing(jefaturasCollection, ...jefaturasArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const jefaturas: IJefaturas = sampleWithRequiredData;
        const jefaturas2: IJefaturas = sampleWithPartialData;
        expectedResult = service.addJefaturasToCollectionIfMissing([], jefaturas, jefaturas2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(jefaturas);
        expect(expectedResult).toContain(jefaturas2);
      });

      it('should accept null and undefined values', () => {
        const jefaturas: IJefaturas = sampleWithRequiredData;
        expectedResult = service.addJefaturasToCollectionIfMissing([], null, jefaturas, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(jefaturas);
      });

      it('should return initial array if no Jefaturas is added', () => {
        const jefaturasCollection: IJefaturas[] = [sampleWithRequiredData];
        expectedResult = service.addJefaturasToCollectionIfMissing(jefaturasCollection, undefined, null);
        expect(expectedResult).toEqual(jefaturasCollection);
      });
    });

    describe('compareJefaturas', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareJefaturas(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareJefaturas(entity1, entity2);
        const compareResult2 = service.compareJefaturas(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareJefaturas(entity1, entity2);
        const compareResult2 = service.compareJefaturas(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareJefaturas(entity1, entity2);
        const compareResult2 = service.compareJefaturas(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
