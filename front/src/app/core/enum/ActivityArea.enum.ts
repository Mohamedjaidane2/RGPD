export enum Activity_area_enum {
    PRIVATE = 'PRIVATE',
    PUBLIC = 'PUBLIC',
    ASSOCIATIVE = 'ASSOCIATIVE',
  }
  
  export const Activity_area_enumMapping: Record<Activity_area_enum, string> = {
    [Activity_area_enum.PRIVATE]: 'Privé',
    [Activity_area_enum.PUBLIC]: 'Publique',
    [Activity_area_enum.ASSOCIATIVE]: 'Association',
  };
  