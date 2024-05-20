# Ze-Impressive-Schwifty

Ze-Impressive-Schwifty is a collection of handcrafted utility functions that are designed for custom projects. Still
they are created in a manner that allows them to be used in a wider context.

## Usage

The modules are published on maven central. To use them add the dependency to your project. For example to add the
`zis-commons` module add the following block:

```xml

<dependency>
    <groupId>org.schlunzis.zis</groupId>
    <artifactId>zis-commons</artifactId>
    <version>0.0.1</version>
</dependency>
```

### Jitpack

To access new features that are not yet published on maven central you can use `jitpack`. Add the following repository
to
your `pom.xml`:

```xml

<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

Then you can add the dependency like this (with short commit hash as version):

```xml

<dependency>
    <groupId>com.github.schlunzis.ze-impressive-schwifty</groupId>
    <artifactId>zis-commons</artifactId>
    <version>f191122</version>
</dependency>
```

Refer to the [jitpack documentation](https://jitpack.io/docs/) for more information on how to use jitpack.

## Example projects

The following lists shows projects that use the `Ze-Impressive-Schwifty` modules and can be used as examples:

- [Polynomial-Regression](https://github.com/JayPi4c/Polynomial-regression)
- [Neuro-Evolution-Vehicles](https://github.com/theBrainsGD/NeuroEvolutionVehicles)
- [Kurtama](https://github.com/schlunzis/Kurtama)

(The examples use the code provided by the `Ze-Impressive-Schwifty` modules. They may not already use the maven
dependencies from maven central. This is about to change soonish...)

## License

tbd
