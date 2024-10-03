# Contributing to **Foodie**

Thank you for considering contributing to our project! We welcome contributions from the community and are grateful for your efforts. Before contributing, please take a moment to read these guidelines.

## Table of Contents
1. [How Can I Contribute?](#how-can-i-contribute)
    - [Reporting Bugs](#reporting-bugs)
    - [Feature Requests](#feature-requests)
    - [Pull Requests](#pull-requests)
2. [Code Guidelines](#code-guidelines)
3. [Setting up the Project](#setting-up-the-project)
4. [Commit Message Guidelines](#commit-message-guidelines)

## How Can I Contribute?

### Reporting Bugs
If you've found a bug in the project, please help us by reporting it:
1. **Search for existing issues** to ensure it's not already reported.
2. **Create a new issue** if it hasn't been reported. Use a descriptive title and provide a clear description, including steps to reproduce, expected behavior, and actual behavior.

### Feature Requests
We welcome feature requests:
1. **Search for existing issues** to avoid duplicates.
2. If you have a new idea, please create an issue describing the feature you'd like to see, why it’s useful, and potential alternatives if applicable.

### Pull Requests
We love receiving pull requests! If you have a fix, improvement, or new feature:
1. Fork the repository.
2. Create a feature branch (`git checkout -b feature-name`).
3. Make your changes.
4. Test your changes locally.
5. Submit your pull request to the `main` or relevant branch.

Before submitting your pull request:
- Make sure your code is properly documented.
- Ensure your changes don’t introduce breaking issues.
- Keep the pull request concise and focused.

## Code Guidelines
To ensure consistency and quality across the project, please adhere to the following guidelines:
- **Follow Kotlin conventions** for Android development.
- **Compose**: Ensure UI components are modular and reusable.
- **Dependency Injection**: Use `Koin` to inject dependencies, keeping the code decoupled.
- **Retrofit2**: Keep API layer clean and well-abstracted with proper error handling.
- **Room**: For a local database, use Room for data persistence.
- **Code Formatting**: Follow the official Kotlin style guide, and use tools like `ktlint` for formatting.
- **Tests**: Add unit tests for new features and ensure existing tests pass. You can use libraries like JUnit and MockK for testing.

## Setting up the Project

1. **Clone the repository**:
    ```bash
    git clone
    ```
    note that you should use `--depth 1` id you have trouble pooling.

2. **Open the project** in Android Studio.

3. **Install dependencies**:
    - Run `gradle sync` to ensure all dependencies are installed.

4. **Build the project**:
    ```bash
    ./gradlew build
    ```

5. **Run the project** on an emulator or physical device.

6. **Run tests**:
    ```bash
    ./gradlew test
    ```

### Environment Setup
- Android Studio: **Arctic Fox** or newer.
- Kotlin version: **1.5+**
- Gradle: **7.x**
- Compose: **1.0+**

## Commit Message Guidelines
We follow a conventional commit message style to keep the project history clean:
- Use descriptive commit messages (e.g., `fix: resolve crash on login` or `feat: add user profile screen`).
- Use imperative language in commit messages.
- Reference relevant issues with `#issue-number` in the message.

### Example Commit Messages:
- `feat: add user authentication via Retrofit2`
- `fix: handle null responses in the API`
- `docs: update README with setup instructions`

---

### Thank you for your contribution!