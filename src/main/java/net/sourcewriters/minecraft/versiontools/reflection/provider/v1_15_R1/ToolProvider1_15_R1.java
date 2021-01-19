package net.sourcewriters.minecraft.versiontools.reflection.provider.v1_15_R1;

import net.sourcewriters.minecraft.versiontools.reflection.ToolProvider;
import net.sourcewriters.minecraft.versiontools.reflection.provider.v1_15_R1.tools.*;

public class ToolProvider1_15_R1 extends ToolProvider<VersionControl1_15_R1> {

	private final SkinTools1_15_R1 skinTools = new SkinTools1_15_R1();
	private final ServerTools1_15_R1 serverTools = new ServerTools1_15_R1();

	protected ToolProvider1_15_R1(VersionControl1_15_R1 versionControl) {
		super(versionControl);
	}

	@Override
	public SkinTools1_15_R1 getSkinTools() {
		return skinTools;
	}

	@Override
	public ServerTools1_15_R1 getServerTools() {
		return serverTools;
	}

}
