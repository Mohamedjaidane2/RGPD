export enum Question_type_enum {
  CHOIX_MULTIPLE = 'CHOIX_MULTIPLE',
  CHOIX_UNIQUE = 'CHOIX_UNIQUE',
}

export const Question_type_enumMapping: Record<
  Question_type_enum | '',
  string
> = {
  [Question_type_enum.CHOIX_MULTIPLE]: 'Choix multiple',
  [Question_type_enum.CHOIX_UNIQUE]: 'Choix unique',
  ['']: 'Pas encore défini',
};
