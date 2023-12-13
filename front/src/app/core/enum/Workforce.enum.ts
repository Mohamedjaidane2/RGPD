export enum Workforce_enum {
  _0_50 = '_0_50',
  _50_100 = '_50_100',
  _100_500 = '_100_500',
  MORE = 'MORE',
}

export const Workforce_enumMapping: Record<
Workforce_enum,
  string
> = {
  [Workforce_enum._0_50]: '0-50',
  [Workforce_enum._50_100]: '50-100',
  [Workforce_enum._100_500]: '100-500',
  [Workforce_enum.MORE]: 'More'
};