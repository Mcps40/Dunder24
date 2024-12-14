export interface IDepartamento {
  id: number;
  nombre_depto?: string | null;
  ubicacion_depto?: string | null;
  presupuesto_depto?: number | null;
}

export type NewDepartamento = Omit<IDepartamento, 'id'> & { id: null };
