export interface IBanner {
  id?: number;
  img?: string;
  url?: string;
  title?: string;
  active?: string;
  position?: string;
  store?: string;
  type?: string;
  html?: string | null;
}

export class Banner implements IBanner {
  constructor(
    public id?: number,
    public img?: string,
    public url?: string,
    public title?: string,
    public active?: string,
    public position?: string,
    public store?: string,
    public type?: string,
    public html?: string | null
  ) {}
}

export function getBannerIdentifier(banner: IBanner): number | undefined {
  return banner.id;
}
