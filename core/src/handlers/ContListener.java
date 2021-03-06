/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.*;
import entities.BoxBody;
import entities.CircleBody;

/**
 *
 * @author karnh7634
 */
public class ContListener implements ContactListener{

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        
        if(fa == null || fb ==null) return;
        if(fa.getUserData() == null || fb.getUserData() == null) return;
        if(isContact(fa,fb)){
//            if(fa.getUserData() == BoxBody){
                
//            }
            BoxBody tba = (BoxBody) fa.getUserData();
            BoxBody tbb = (BoxBody) fb.getUserData();
            tba.hit();
            tbb.hit();
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        
        if(fa == null || fb ==null) return;
        if(fa.getUserData() == null || fb.getUserData() == null) return;
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
    
    private boolean isContact(Fixture a, Fixture b){
        if(a.getUserData() instanceof BoxBody || b.getUserData() instanceof CircleBody){
            if(a.getUserData() instanceof CircleBody || b.getUserData() instanceof BoxBody){
                return true;
            }
        } return false;
//        return(a.getUserData() instanceof BoxBody && b.getUserData() instanceof BoxBody);
    }
    
}
