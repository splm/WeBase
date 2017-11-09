package me.splm.app.inject.processor.code;


public class WeModifier {
    private int p;
    private int sta;
    private int syn;
    private int abs;
    public WeModifier(int p,int sta,int syn,int abs) {
        this.p=p;
        this.sta=sta;
        this.syn=syn;
        this.abs=abs;
    }
    public int conduct(){
        return (p<<24)|(sta<<16)|(syn<<8)|abs;
    }
}
