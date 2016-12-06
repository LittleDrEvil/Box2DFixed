package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import entities.BoxBody;
import entities.CircleBody;
import java.util.HashSet;
import java.util.Set;
import managers.GameStateManager;
import static utils.Constants.PPM;
import utils.TiledObjectUtil;

public class PlayState extends GameState {

    private BoxBody bbPlayer, bbObj1, bbObj2;
    private CircleBody arBodies[];
    private Contact contact;
    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;
    private float fSpeed;
    private Box2DDebugRenderer b2dr;
    private World world;
    private Boolean isLeft = false;
    private Texture texture;
    private int nJump = 0;
    
    
    private int nArrayMax = 10;
    public PlayState(GameStateManager gsm){
        super(gsm);
        arBodies = new CircleBody[10];
        world = new World(new Vector2(0,-9.8f), false);
        b2dr = new Box2DDebugRenderer();
        map = new TmxMapLoader().load("MapTest3.tmx");
        tmr = new OrthogonalTiledMapRenderer(map);
        TiledObjectUtil.parseTiledObjectLayer(world, map.getLayers().get("collision").getObjects());
        
        
        bbPlayer = new BoxBody(world, "PLAYER", 0, 400, 20);
        bbObj1 = new BoxBody(world, "OBJ1", 20, 400, 10);
        bbObj2 = new BoxBody(world, "OBJ2", -20, 400, 10);
        for (int i = 0; i < 10; i++) {
            arBodies[i] = new CircleBody(world, ("Obj" + i), i*10, 400, 10);
        }
        
    }
    
    @Override
    public void update(float delta) {
        world.step(1/60f, 6, 2);
        
        inputUpdate(delta);
        cameraUpdate(bbPlayer.body);
        
        tmr.setView(camera);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.25f, 0.25f, 0.25f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        tmr.render();
        b2dr.render(world, camera.combined.scl(PPM));
    }

    @Override
    public void dispose() {
        b2dr.dispose();
        world.dispose();
    }
    
    public void cameraUpdate(Body body1){
        Vector3 position = camera.position;
        // a + (b - a) * lerp
        // b = target 
        // a = current camera position
        position.x = camera.position.x + (body1.getPosition().x * PPM - camera.position.x) * 0.1f;
        position.y = camera.position.y + (body1.getPosition().y * PPM - camera.position.y) * 0.1f;
        
        camera.position.set(position);
        camera.update();
    }
    
    public void inputUpdate(float delta){
        int horizontalForce = 0, x=0 , y=0;
       
        if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            x--;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            x++;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.W) || Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            bbPlayer.body.applyForceToCenter(0, 800, false);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            y--;
        }
        if(x!= 0 )
            bbPlayer.body.setLinearVelocity( x * 350 * delta, bbPlayer.body.getLinearVelocity().y);
        if(y!= 0 )
            bbPlayer.body.setLinearVelocity(bbPlayer.body.getLinearVelocity().x, y*350*delta);
    }
}
