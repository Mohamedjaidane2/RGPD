export enum Penalty_status_enum {
  CHECKED = 'CHECKED',
  UNCHECKED = 'UNCHECKED',
  INDETERMINATE = "INDETERMINATE"
}

export const Penalty_status_enumMapping: Record<Penalty_status_enum|"", string> = {
  [Penalty_status_enum.CHECKED]: "Coché",
  [Penalty_status_enum.UNCHECKED]: "Non coché",
  [Penalty_status_enum.INDETERMINATE]: "Indéterminé",
  [""]: "Pas encore défini",
};
