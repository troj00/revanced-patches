package app.revanced.patches.music.layout.tabletmode.patch

import app.revanced.patcher.annotation.Description
import app.revanced.patcher.annotation.Name
import app.revanced.patcher.annotation.Version
import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.addInstructions
import app.revanced.patcher.extensions.instruction
import app.revanced.patcher.patch.annotations.Patch
import app.revanced.patcher.patch.annotations.DependsOn
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.PatchResult
import app.revanced.patcher.patch.PatchResultSuccess
import app.revanced.patches.music.layout.tabletmode.fingerprints.TabletLayoutFingerprint
import app.revanced.patches.music.misc.integrations.patch.MusicIntegrationsPatch
import app.revanced.patches.music.misc.resourceid.patch.SharedResourcdIdPatch
import app.revanced.patches.music.misc.settings.patch.MusicSettingsPatch
import app.revanced.shared.annotation.YouTubeMusicCompatibility
import app.revanced.shared.util.integrations.Constants.MUSIC_SETTINGS_PATH

@Patch
@Name("enable-tablet-mode")
@Description("Enable landscape mode on phone.")
@DependsOn(
    [
        MusicIntegrationsPatch::class,
        MusicSettingsPatch::class,
        SharedResourcdIdPatch::class
    ]
)
@YouTubeMusicCompatibility
@Version("0.0.1")
class TabletModePatch : BytecodePatch(
    listOf(
        TabletLayoutFingerprint
    )
) {
    override fun execute(context: BytecodeContext): PatchResult {
        val result = TabletLayoutFingerprint.result!!
        val method = result.mutableMethod

        val endIndex = result.scanResult.patternScanResult!!.endIndex
        val insertIndex = endIndex + 1

        method.addInstructions(
            insertIndex, """
                invoke-static {p0}, $MUSIC_SETTINGS_PATH->enableTabletMode(Z)Z
                move-result p0
            """
        )

        return PatchResultSuccess()
    }
}
