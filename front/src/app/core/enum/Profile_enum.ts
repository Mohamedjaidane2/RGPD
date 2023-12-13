export enum Profile_enum {
  COLLABORATEUR = 'COLLABORATEUR',
  RESPONSABLE_METIER = 'RESPONSABLE_METIER',
  DIRIGANT = 'DIRIGANT',
}

export const Profile_enumMapping: Record<Profile_enum, string> = {
  [Profile_enum.COLLABORATEUR]: 'Collaborateur',
  [Profile_enum.RESPONSABLE_METIER]: 'Responsable metier',
  [Profile_enum.DIRIGANT]: 'Dirigant',
};
