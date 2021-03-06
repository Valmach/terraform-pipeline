## [ValidateFormatPlugin](../src/ValidateFormatPlugin.groovy)

Enable this plugin to run `terraform fmt -check` as part of the TerraformValidateStage.  If no changes are necessary, TerraformValidateStage will pass.  If any format changes are necessary, the TerraformValidateStage will fail.

```
// Jenkinsfile
@Library(['terraform-pipeline']) _

Jenkinsfile.init(this)

ValidateFormatPlugin.init()

// Runs `terraform fmt -check` in addition to `terraform validate`.
// TerraformValidateStage fails if code requires validation.
def validate = new TerraformValidateStage()
def deployQA = new TerraformEnvironmentStage('qa')
def deployUat = new TerraformEnvironmentStage('uat')
def deployProd = new TerraformEnvironmentStage('prod')

validate.then(deployQA)
        .then(deployUat)
        .then(deployProd)
        .build()
```

Additional options are available, to search directories recursively, and to display diffs.

```
// Jenkinsfile
@Library(['terraform-pipeline']) _

Jenkinsfile.init(this)

ValidateFormatPlugin.init()
TerraformFormatCommand.withRecursive()
                      .withDiff()

// Runs `terraform fmt -check` in addition to `terraform validate`.
// TerraformValidateStage fails if code requires validation.
def validate = new TerraformValidateStage()
def deployQA = new TerraformEnvironmentStage('qa')
def deployUat = new TerraformEnvironmentStage('uat')
def deployProd = new TerraformEnvironmentStage('prod')

validate.then(deployQA)
        .then(deployUat)
        .then(deployProd)
        .build()
```

