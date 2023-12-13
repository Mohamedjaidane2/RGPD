export class User{
constructor(token:string) {
}
  // @ts-ignore
  get token(){
    if(!this.token){
      return null
    }
    return  this.token();
  }
}
