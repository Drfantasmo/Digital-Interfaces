package us.fantasmo.digitalinterfaces.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import us.fantasmo.digitalinterfaces.DigitalInterfacesMod;
import us.fantasmo.digitalinterfaces.Reference;
import us.fantasmo.digitalinterfaces.message.client.DIMessageUpdate;
import us.fantasmo.digitalinterfaces.tileentity.TileEntityDigitalNode;

public class GuiNode extends GuiScreen {

	private BlockPos pos;
	private TileEntity node;
	private GuiTextField text;
	private ArrayList<String> tags;

	public GuiNode(TileEntity node, String[] tags) {
		this.node = node;
		this.pos = node.getPos();
		this.tags = new ArrayList<String>();
		for (String s : tags)
			this.tags.add(s);
	}

	private int lX, lW, lY, lM, scrollPos;

	@Override
	public void initGui() {
		ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
		int pad = 10;
		int height = 20;
		int percent = 80;
		this.text = new GuiTextField(0, this.fontRendererObj, (resolution.getScaledWidth() - backgroundWidth) / 2 + pad,
				(resolution.getScaledHeight() + backgroundHeight) / 2 - height - pad,
				((backgroundWidth - (2 * pad)) * percent) / 100, height);
		text.setMaxStringLength(TileEntityDigitalNode.MAX_TAG_LENGTH);
		this.text.setFocused(true);
		this.buttonList.add(new GuiButton(1, (resolution.getScaledWidth() + backgroundWidth) / 2 - pad - 40,
				(resolution.getScaledHeight() - backgroundHeight) / 2 + pad, 40, 20, "Close"));
		this.buttonList.add(new GuiButton(2, (resolution.getScaledWidth() + backgroundWidth) / 2 - pad - 40 - 40 - 5,
				(resolution.getScaledHeight() - backgroundHeight) / 2 + pad, 40, 20, "Save"));
		this.buttonList.add(new GuiButton(3, (resolution.getScaledWidth() - backgroundWidth) / 2 + pad,
				(resolution.getScaledHeight() - backgroundHeight) / 2 + pad, 40, 20, "Add"));
		this.buttonList.add(new GuiButton(4, (resolution.getScaledWidth() - backgroundWidth) / 2 + pad + 40 + 5,
				(resolution.getScaledHeight() - backgroundHeight) / 2 + pad, 40, 20, "Delete"));
		this.buttonList.add(new GuiButton(5, (resolution.getScaledWidth() + backgroundWidth) / 2 - pad - 20,
				(resolution.getScaledHeight() + backgroundHeight) / 2 - pad - 20, 20, 20, "↓"));
		this.buttonList.add(new GuiButton(6, (resolution.getScaledWidth() + backgroundWidth) / 2 - pad - 20 - 20 - 3,
				(resolution.getScaledHeight() + backgroundHeight) / 2 - pad - 20, 20, 20, "↑"));
		this.buttonList.get(3 - 1).enabled = false;
		this.buttonList.get(4 - 1).enabled = false;
		lX = (resolution.getScaledWidth() - backgroundHeight) / 2 + pad;
		lW = (resolution.getScaledWidth() + backgroundHeight) / 2 - pad;
		lY = (resolution.getScaledHeight() - backgroundHeight) / 2 + pad + 20 + 5;
		int lB = (resolution.getScaledHeight() + backgroundHeight) / 2 - pad - 20 - 5;
		lM = (int) Math.floor((lB - lY) / 20);
		scrollPos = 0;
	}

	@Override
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
		int wheelState = Mouse.getEventDWheel();
		if (wheelState != 0) {
			int x = Mouse.getX();
			int y = Mouse.getY();
			if (wheelState > 0)
				scrollDec();
			if (wheelState < 0)
				scrollInc();
		}
	}

	@Override
	public void updateScreen() {
		if (!stillExists())
			if (mc.currentScreen == this) {
				this.mc.thePlayer.closeScreen();
				return;
			}
		super.updateScreen();
		Collections.sort(tags);
		this.text.updateCursorCounter();
		this.buttonList.get(3 - 1).enabled = textGood();
		if (index >= this.tags.size())
			index = -1;
		this.buttonList.get(4 - 1).enabled = (index >= 0);
		this.buttonList.get(5 - 1).enabled = scrollPos < tags.size() - 5;
		this.buttonList.get(6 - 1).enabled = scrollPos > 0;
	}

	@Override
	protected void keyTyped(char par1, int par2) throws IOException {
		super.keyTyped(par1, par2);
		this.text.textboxKeyTyped(par1, par2);
		int i = par1;
		if (i == 13 && text.isFocused()) {
			addTag();
		}
	}

	private void addTag() {
		if (textGood()) {
			tags.add(this.text.getText());
			this.text.setText("");
		}
	}

	private void delTag() {
		this.tags.remove(getSelected());
		index = -1;
	}

	private void scrollInc() {
		if (scrollPos < tags.size() - 5)
			scrollPos++;
	}

	private void scrollDec() {
		if (scrollPos > 0)
			scrollPos--;
	}

	public boolean textGood() {
		String s = this.text.getText();
		if (s.trim().length() == 0)
			return false;
		for (String a : tags)
			if (a.equalsIgnoreCase(s))
				return false;
		return true;
	}

	public int getSelected() {
		return index + scrollPos;
	}

	private int index = -1;

	@Override
	protected void mouseClicked(int x, int y, int btn) throws IOException {
		super.mouseClicked(x, y, btn);
		this.text.mouseClicked(x, y, btn);
		if (x >= lX - 37 && x <= lW + 36 && y >= lY + 5 && y <= 5 + lY + 20 * lM) {
			int yy = y - lY + 5 - 10;
			int sel = (int) Math.floor(yy / 20.0);
			index = sel;
			if (index > 4)
				index = 4;
			if (index < 0)
				index = 0;
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		switch (button.id) {
		case 1:
			mc.thePlayer.closeScreen();
			return;
		case 2:
			String[] as = new String[tags.size()];
			for (int i = 0; i < tags.size(); i++)
				as[i] = tags.get(i);
			DigitalInterfacesMod.simpleNetwork.sendToServer(new DIMessageUpdate(pos, as));
			mc.thePlayer.closeScreen();
			return;
		case 3:
			addTag();
			break;
		case 4:
			delTag();
			break;
		case 5:
			this.scrollInc();
			break;
		case 6:
			this.scrollDec();
			break;
		}
	}

	private int backgroundWidth = 255;
	private int backgroundHeight = 180;

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
		int bX = (resolution.getScaledWidth() - backgroundWidth) / 2;
		int bY = (resolution.getScaledHeight() - backgroundHeight) / 2;
		this.drawDefaultBackground();
		mc.renderEngine.bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/node.png"));
		drawTexturedModalRect(bX, bY, 0, 0, backgroundWidth, backgroundHeight);
		this.text.drawTextBox();
		this.drawRect(lX - 37 - 1, lY + 5 - 1, lW + 37, 5 + lY + 20 * lM + 1, 0xFFA0A0A0);
		this.drawRect(lX - 37, lY + 5, lW + 37 - 1, 5 + lY + 20 * lM, 0xFF000000);
		for (int i = scrollPos; i < tags.size() && (i - scrollPos < lM) && scrollPos >= 0; i++) {
			this.drawString(this.fontRendererObj, tags.get(i), lX - 30, lY + 20 * (i - scrollPos) + 10,
					getSelected() != i ? 0xFFFFFFFF : 0xFF1f1fff);
			String q = (i + 1) + "/" + tags.size();
			int qa = this.fontRendererObj.getStringWidth(q);
			this.drawString(this.fontRendererObj, q, lW + 37 - 5 - qa, lY + 20 * (i - scrollPos) + 10, 0xFF424242);
		}
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	private boolean stillExists() {
		if (node == null)
			return false;
		TileEntity t = Minecraft.getMinecraft().theWorld.getTileEntity(pos);
		if (!(t instanceof TileEntityDigitalNode))
			return false;
		return true;
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

}
