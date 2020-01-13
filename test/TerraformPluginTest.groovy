import org.junit.*
import org.junit.runner.RunWith
import de.bechte.junit.runners.context.HierarchicalContextRunner

import static org.hamcrest.Matchers.*
import static org.junit.Assert.*
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Matchers.*

@RunWith(HierarchicalContextRunner.class)
class TerraformPluginTest {

    class VersionDetection {
        @After
        void resetVersion() {
            TerraformPlugin.resetVersion()
        }

        @Test
        void usesDefaultIfNoFilePresent() {
            def plugin = spy(new TerraformPlugin())
            doReturn(false).when(plugin).fileExists(TerraformPlugin.TERRAFORM_VERSION_FILE)

            def foundVersion = plugin.detectVersion()

            assertEquals(TerraformPlugin.DEFAULT_VERSION, foundVersion.version)
        }

        @Test
        void usesFileIfPresent() {
            def expectedVersion =  '0.12.0-foobar'
            def plugin = spy(new TerraformPlugin())
            doReturn(true).when(plugin).fileExists(TerraformPlugin.TERRAFORM_VERSION_FILE)
            doReturn(expectedVersion).when(plugin).readFile(TerraformPlugin.TERRAFORM_VERSION_FILE)

            def foundVersion = plugin.detectVersion()

            assertEquals(expectedVersion, foundVersion.version)
        }
    }

    class WithVersion {
        @After
        void resetVersion() {
            TerraformPlugin.resetVersion()
        }

        @Test
        void usesVersionEvenIfFileExists() {
            TerraformPlugin.withVersion('2.0.0')
            assertEquals('2.0.0', TerraformPlugin.version.version)
        }
    }
}
