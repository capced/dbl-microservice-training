// This config was generated using a preset.
// Please see the handbook for more information: https://github.com/stryker-mutator/stryker-handbook/blob/master/stryker/guides/angular.md#angular
module.exports = function (config) {
  config.set({
    mutate: [
      'src/**/*.ts',
      '!src/**/index.ts',
      '!src/**/*.spec.ts',
      '!src/**/*.module.ts',
      '!src/test.ts',
      '!src/environments/*.ts',
      '!src/polyfills.ts',
      '!src/main.ts',
    ],
    mutator: 'typescript',
    testRunner: 'karma',
    karma: {
      configFile: 'karma.conf.js',
      projectType: 'angular-cli',
      config: {
        logLevel: 'trace',
        browsers: ['ChromeHeadlessCI'],
      },
    },
    reporters: ['progress', 'clear-text', 'html'],
    maxConcurrentTestRunners: 2, // Recommended to use about half of your available cores when running stryker with angular.
    coverageAnalysis: 'off',
    checkers: ['typescript'],
    tsconfigFile: 'tsconfig.json',
  });
};
