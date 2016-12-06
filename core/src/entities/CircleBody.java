/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import utils.Constants;

/**
 *
 * @author karnh7634
 */
public class CircleBody {
        public Body body;
    public String id;
    public int nWidth;
    
    public CircleBody(World world, String id, float x, float y, int nWidth){
        this.id = id;
        this.nWidth = nWidth;
        createCircleBody(world, x , y);
        body.setLinearDamping(0.1f);
    }
    
    private void createCircleBody(World world, float x, float y){
        BodyDef bdef = new BodyDef();
        bdef.fixedRotation = false;
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(x/Constants.PPM, y/Constants.PPM);
        
        CircleShape shape = new CircleShape();
        shape.setRadius(16/Constants.PPM);
        
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.density = 10f;
        fdef.friction = 10f;
        
        this.body = world.createBody(bdef);
        this.body.createFixture(fdef).setUserData(this);
        
    }
    
    
    public void hit(){
        System.out.println(id + " : hiteroni");
    }
}
