package render;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Material {
	public String identifier;
	public Color ambientColor;
	public Color diffuseColor;
	public Color specularColor;
	public double transparency;
	public double density;
	public BufferedImage ambiientMap;
	public BufferedImage diffuseMap;
	public BufferedImage textureMap;
}
