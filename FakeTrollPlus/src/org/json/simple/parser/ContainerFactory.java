package org.json.simple.parser;

import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public abstract interface ContainerFactory {
	
	public abstract Map createObjectContainer();

	public abstract List creatArrayContainer();
}