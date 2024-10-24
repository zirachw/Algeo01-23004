import numpy as np

# Original data extracted from the table
humidity = np.array([72.4, 41.6, 34.3, 35.1, 10.7, 12.9, 8.3, 20.1, 72.2, 24.0, 
                     23.2, 47.4, 31.5, 11.2, 73.3, 75.4, 96.6, 107.4, 107.4, 54.9])
temperature = np.array([76.3, 70.3, 77.1, 68.0, 79.0, 67.4, 66.8, 76.9, 77.7, 67.7,
                        76.8, 86.6, 76.9, 86.0, 76.3, 77.9, 78.7, 86.8, 86.8, 70.9])
pressure = np.array([29.18, 29.35, 29.24, 29.27, 29.78, 29.39, 29.69, 29.48, 29.09, 29.60,
                     29.38, 29.35, 29.63, 29.48, 29.40, 29.28, 29.29, 29.03, 29.03, 29.37])
nitrous_oxide = np.array([0.90, 0.91, 0.96, 0.89, 1.00, 1.10, 1.15, 1.03, 0.77, 1.07, 
                          1.07, 0.94, 1.10, 1.10, 0.91, 0.87, 0.78, 0.82, 0.82, 0.95])

# Manually create quadratic and interaction terms
X = np.column_stack((
    np.ones_like(humidity),           # Bias term (intercept)
    humidity,                        # Humidity (x1)
    temperature,                     # Temperature (x2)
    pressure,                        # Pressure (x3)
    humidity ** 2,                   # Humidity^2 (x1^2)
    humidity * temperature,          # Humidity * Temperature (x1 * x2)
    humidity * pressure,             # Humidity * Pressure (x1 * x3)
    temperature ** 2,                # Temperature^2 (x2^2)
    temperature * pressure,          # Temperature * Pressure (x2 * x3)
    pressure ** 2                    # Pressure^2 (x3^2)
))
print("X:")
print(X)
# Apply the normal equation to compute the coefficients
X_transpose = X.T
print("X_transpose:")
print(X_transpose)
XTX = X_transpose.dot(X)
print("XTX:")
X_inv = np.linalg.inv(XTX)
print("X_inv:")
print(X_inv)
X_inv_T = X_inv.dot(X_transpose)
print("X_inv_T:")
print(X_inv_T)
beta = X_inv_T.dot(nitrous_oxide)
print("Beta:")
print(beta)

# Display the beta values (coefficients)
beta_labels = ['Intercept', 'Humidity', 'Temperature', 'Pressure', 
               'Humidity^2', 'Humidity * Temperature', 'Humidity * Pressure',
               'Temperature^2', 'Temperature * Pressure', 'Pressure^2']

print("=== Beta Values (Coefficients) ===")
for coef, label in zip(beta, beta_labels):
    print(f"{label}: {coef:.4f}")

# Predicting the Nitrous Oxide value for given inputs
def predict_nitrous_oxide(humidity, temperature, pressure):
    # Manually calculate the predicted value using the regression equation
    y_pred = (
        beta[0] + beta[1] * humidity + beta[2] * temperature + beta[3] * pressure +
        beta[4] * humidity ** 2 + beta[5] * humidity * temperature +
        beta[6] * humidity * pressure + beta[7] * temperature ** 2 +
        beta[8] * temperature * pressure + beta[9] * pressure ** 2
    )
    return y_pred

# Input values for prediction
input_humidity = 50
input_temperature = 76
input_pressure = 29.30

# Output the prediction process
print("\n=== Prediction Process ===")
print(f"Input values: Humidity={input_humidity}%, Temperature={input_temperature}°F, Pressure={input_pressure}")
predicted_no = predict_nitrous_oxide(input_humidity, input_temperature, input_pressure)
print(f"Estimated Nitrous Oxide value: {predicted_no:.4f}")

import numpy as np

# Set print options to format the output with better alignment
np.set_printoptions(precision=4, suppress=True, linewidth=150)

# Print the matrix X
print("Matrix X:")
for row in X:
    print(" ".join(f"{elem:10.4f}" for elem in row))

print("\nMatrix X_transpose:")
for row in X_transpose:
    print(" ".join(f"{elem:10.4f}" for elem in row))


# If you want to print other matrices similarly, you can use the same approach.
print("\nMatrix XTX:")
for row in XTX:
    print(" ".join(f"{elem:10.4f}" for elem in row))

print("\nMatrix X_inv:")
for row in X_inv:
    print(" ".join(f"{elem:10.4f}" for elem in row))

print("\nMatrix X_inv_T:")
for row in X_inv_T:
    print(" ".join(f"{elem:10.4f}" for elem in row))

print("\nBeta:")
for row in beta:
    print(f"{row:10.4f}")
