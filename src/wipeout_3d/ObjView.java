package wipeout_3d;

import java.io.IOException;

import javafx.scene.Group;
import javafx.scene.Node;
//import javafx.scene.shape.CullFace;
import javafx.scene.shape.MeshView;

/**
 * Expanded to include copy constructor
 * 
 * @author mike slattery
 * @version may 2018
 *
 */
public class ObjView {

	//Code to use ObjImporter:
	final Group res = new Group();

    public void load(String fileUrl) throws IOException {
	    ObjImporter reader = new ObjImporter(fileUrl);
	    for (String mesh : reader.getMeshes())
	    	res.getChildren().add(reader.buildMeshView(mesh));
    }

    public Group getRoot() {
    	return res;
    }
    
    MeshView copyMeshView(MeshView mv1)
    {
    	MeshView mv2 = new MeshView(mv1.getMesh());
    	mv2.setId(mv1.getId());
    	mv2.setMaterial(mv1.getMaterial());
    	mv2.setCullFace(mv1.getCullFace());
    	return mv2;
    }
    
    //copy constructor
    public ObjView(ObjView orig)
    {
    	for (Node n: orig.res.getChildren())
    	{
    		MeshView mv = (MeshView)n;
    		res.getChildren().add(copyMeshView(mv));
    	}
    }
    
    //need explicit default constructor
    public ObjView(){}
	
}
