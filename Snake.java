import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Snake here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Snake extends Actor
{
    /**
     * Act - do whatever the Snake wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private boolean alive=true;
    private int count;
    private int speed=2;
    private boolean vulnerable;
    private Elephant elephant;
    private int yVelocity=Greenfoot.getRandomNumber(5)-9;
    private int xDirection;
    private int elephantY;
    private int snakeDeadAtY;
    private int pointAtElephantInt;
    private double xDiff;
    private double yDiff;
    private double pointAtElephantDoubleRad;
    private double pointAtElephantDoubleDeg;
    GreenfootImage image = new GreenfootImage("images/snake.png");
    
    
    public void act()
    {
        setImage(image);
        count++;
        if(alive){
            
            if(count%15==0){
                xDiff=((Elephant)getWorld().getObjects(Elephant.class).get(0)).getX()-getX();
                yDiff=((Elephant)getWorld().getObjects(Elephant.class).get(0)).getY()-getY();
                pointAtElephantDoubleRad=Math.atan(yDiff/xDiff);
                pointAtElephantDoubleDeg=Math.toDegrees(pointAtElephantDoubleRad);
                pointAtElephantInt= (int)Math.round(pointAtElephantDoubleDeg);
                if(pointAtElephantInt<0){
                    setRotation(pointAtElephantInt+180);
                    }
                else{
                    setRotation(pointAtElephantInt);
                }
            }
            
            speed=(int)Math.round((100.0-Math.abs(pointAtElephantInt))/40.0);
            move(speed+1);
            setLocation(getX(),getY());
        }
        
        MyWorld world = (MyWorld) getWorld();
        if(alive){
            if (isTouching(Elephant.class)){
                if(alive){
                    if(((Elephant)getWorld().getObjects(Elephant.class).get(0)).getRolling()==true)
                    {
                        die();
                        xDirection=((Elephant)getWorld().getObjects(Elephant.class).get(0)).getDirection();
                        snakeDeadAtY=getY();
                    }
                    else
                    {
                        world.gameOver();
                    }
                }
            }
        }
        if (alive==false){   
            
            image.scale(138,44);
            eat();
            
            setLocation(getX()+xDirection*20,getY()+yVelocity);
            if(xDirection==-1){
                setRotation(11+180);
            }
            if(xDirection==1){
                setRotation(180-11+180);
            }
            if(getX()<=0||getX()>=600)
            {
                xDirection=xDirection*-1;
            }
            if(getY()<-10){
                getWorld().removeObject(this);
            }
        }
        if (count%30==0){
            System.out.print(getAlive());
        }
    }
    public void die()
    {
        alive=false;
        System.out.println("i am dead");
    }
    
    public boolean getAlive()
    {
        return alive;
    }
    int goldenAppleChance = 10;
    public void eat()
    {
        MyWorld world = (MyWorld) getWorld();
        
        int goldenAppleDraw = Greenfoot.getRandomNumber(goldenAppleChance);
        if(isTouching(Apple.class))
        {
            if(isTouching(GoldenApple.class))
            {
                world.summonAppleWave(50);
            }
            if(isTouching(SpeedApple.class))
            {
                //speedCooldown=500;
            }
            removeTouching(Apple.class);
    
            world.applesCount-=1;
            world.increaseScore();
            if (world.gamePhase=="normal"){
                if(world.applesCount<=4){
                    if((goldenAppleDraw==0))
                    {
                        world.createGoldenApple();
                        goldenAppleChance = 50;
                        
                    }
                    else
                    {
                        goldenAppleChance-=1;
                        world.createApple();
                    }
                }
            }               

        }
    }
}
