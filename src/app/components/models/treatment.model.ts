// src/app/models/treatment.model.ts

export interface Treatment {
    treatmentId: number;
    diagnosis: string;
    treatmentMethod: string;
    notes: string;
    result: string;
    filePath: string;
    treatmentDate: Date |null;
    appointmentId: number;
    userId: number;
  }
