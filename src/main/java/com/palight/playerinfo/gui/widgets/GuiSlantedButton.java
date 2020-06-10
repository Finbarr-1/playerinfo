package com.palight.playerinfo.gui.widgets;

import com.palight.playerinfo.modules.NoteBlockUtil;
import com.palight.playerinfo.util.MidiUtil;
import com.palight.playerinfo.util.NumberUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.world.NoteBlockEvent;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import java.util.List;

import static com.palight.playerinfo.PlayerInfo.DATA_FOLDER;

public class GuiSlantedButton extends GuiCustomWidget {

    public String displayString;
    private int segmentWidth = 16;

    public GuiSlantedButton(int id, int xPosition, int yPosition, int width, int height, String displayString) {
        super(id, xPosition, yPosition, width, height);
        this.displayString = displayString;
        this.width = width - (width % segmentWidth); // MUST BE A MULTIPLE OF segmentWidth FOR HORIZONTAL TILING TO WORK
    }

    @Override
    public void drawWidget(Minecraft mc, int mouseX, int mouseY) {
        super.drawWidget(mc, mouseX, mouseY);
        int segments = width / 16;

        if (segments == 1) {
            // no tiling for 1 segment wide button
            drawTexturedModalRect(xPosition, yPosition, 0, 96, width, height);
        } else if (segments > 1) {

            for (int i = 0; i < segments; i++) {
                int xOff;

                if (i == 0) {
                    xOff = 0;
                } else if (i == segments - 1) {
                    xOff = segmentWidth * 2;
                } else {
                    xOff = segmentWidth;
                }

                drawTexturedModalRect(xPosition + (i * segmentWidth), yPosition, xOff, 80, segmentWidth, height);
            }
        }

        this.drawCenteredString(mc.fontRendererObj, displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, 0xffffffff);
    }

    public boolean mousePressed(int mouseX, int mouseY) {

        if (!this.enabled) return false;

        if (NumberUtil.pointIsBetween(mouseX, mouseY, xPosition, yPosition, xPosition + width, yPosition + height)) {
            try {
                Sequence sequence = MidiUtil.getSequence(DATA_FOLDER + "/country_roads.mid");
//                MidiUtil.playMidiSequence(sequence);
                List<ShortMessage> smList = MidiUtil.readMidi(sequence);

                long tickLength = sequence.getTickLength();

                for (ShortMessage sm : smList) {
                    NoteBlockEvent.Note note = NoteBlockUtil.getNote(MidiUtil.toMCNote(sm));
                    Thread.sleep(500);
                    Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("note.harp"), NoteBlockUtil.getPitch(note)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }

        return false;
    }
}
